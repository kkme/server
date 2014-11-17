package com.hifun.soul.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * 具有索引值的对象列表.
 * <p>
 * 机制:使用数组管理对象,类似{@link java.util.ArrayList},但数组分配后不能改变长度;
 * 增加一个元素时,先搜索数组中的空值,如果存在,则将该空值的索引分配给新的元素,否则增加失败;
 * 删除一个元素时,只将数组中的该元素索引置空
 * </p>
 * <p>
 * 遍历的方式:对于只读的遍历,可以用for-each方式;
 * 对于可能有删除操作的遍历,请显式地使用迭代器并调用iterator.remove方法删除.<br/>
 * 注意:在遍历中直接调用列表的remove方法,可能会导致遍历不全.
 * </p>
 * <p>
 * 没有引入线程安全机制,如需多线程访问,请手动加锁.
 * </p>
 * 
 * @author crazyjohn
 *
 * @param <T> 具有索引值的对象
 */
public class IndexedObjectList<T extends IndexedObject> implements Iterable<T> {
	
	protected Object[] objects;
	protected int size;
	protected int position;
	
	/**
	 * 构造一个新的列表
	 * 
	 * @param capacity 列表的总长度
	 */
	public IndexedObjectList(int capacity) {
		objects = new Object[capacity];
		size = 0;
		position = 0;
	}
	
	/**
	 * 增加一个对象
	 * 
	 * @param obj 非null的可索引对象.增加成功后,会设置对象的索引值.
	 * @return 如果对象已在列表中,或列表已满,则增加失败,返回false,否则返回true
	 * @exception NullPointerException 如果对象为null
	 */
	public boolean add(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (size >= objects.length) {
			return false;
		}
		if (contains(obj)) {
			return false;
		}
		int index = position;
		while (objects[index] != null) {
			index = increase(index);
//			if (index == position) {
//				return false;
//			}
		}
		objects[index] = obj;
		obj.setIndex(index);
		size++;
		position = increase(index);
		return true;
	}
	
	private int increase(int index) {
		index++;
		if (index == objects.length) {
			index = 0;
		}
		return index;
	}

	/**
	 * 删除对象.实际上是根据对象的索引值进行删除操作,见{@link #removeAt}方法
	 * 
	 * @param obj 如果对象为null,则无影响
	 * @exception IndexOutOfBoundsException 对象的索引值小于0或大于等于列表的总长度
	 */
	public void remove(T obj) {
		if (obj != null) {
			removeAt(obj.getIndex());
		}
	}
	
	/**
	 * 删除指定索引的对象.如果删除成功,则设置对象的索引值为-1.
	 * 可以在for-each遍历中调用remove和removeAt方法.
	 * 
	 * @param index
	 * @exception IndexOutOfBoundsException 索引值小于0或大于等于列表的总长度
	 */
	@SuppressWarnings("unchecked")
	public void removeAt(int index) {
		checkRange(index);
		T obj = (T) objects[index];
		if (obj != null) {
			objects[index] = null;
			obj.setIndex(-1);
			size--;
		}
	}
	
	/**
	 * 检查索引值的范围是否有效,无效则抛出异常
	 * 
	 * @param index
	 */
	protected void checkRange(int index) {
		if (index < 0 || index >= objects.length) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * 获取指定索引的对象
	 * 
	 * @param index
	 * @return 如果该索引无对象,或索引值大于列表的总长度,则返回null
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index >= 0 && index < objects.length) {
			return (T) objects[index];
		}
		return null;
	}
	
//	/**
//	 * 设置指定索引为某个对象.会设置对象的索引值为指定索引
//	 * 
//	 * @param index
//	 * @param obj
//	 * @exception IllegalArgumentException 对象为空
//	 */
//	public void set(int index, T obj) {
//		if (obj == null) {
//			throw new IllegalArgumentException("obj");
//		}
//		// 如果该索引之前没有对象,则对象总数增加
//		if (objects[index] == null) {
//			size++;
//		}
//		objects[index] = obj;
//		obj.setIndex(index);
//	}

	/**
	 * 判断列表中是否包含某个对象
	 * 
	 * @param obj
	 * @return 如果对象为null或对象的索引无效,则返回false;否则返回列表中该索引的对象和对象的equals结果.
	 */
	public boolean contains(T obj) {
		if (obj == null || obj.getIndex() < 0) {
			return false;
		}
		return obj.equals(get(obj.getIndex()));
	}
	
	/**
	 * 获取列表的总长度
	 * 
	 * @return
	 */
	public int capacity() {
		return objects.length;
	}
	
	/**
	 * 获取列表包含对象的个数
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}
	
	/**
	 * 删除列表中所有对象
	 */
	@SuppressWarnings("unchecked")
	public void removeAll() {
		for (int i = 0; i < objects.length; i++) {
			T obj = (T) objects[i];
			if (obj != null) {
				objects[i] = null;
				obj.setIndex(-1);
			}
		}
		size = 0;
		position = 0;
	}

	/**
	 * 获取迭代器对象.该迭代器只返回非空的对象.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Itr();
	}
	
	private class Itr implements Iterator<T> {

		private int index = -1;
		private int counter = 0;
		
		@Override
		public boolean hasNext() {
			return counter < size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			T result = null;
			while (counter < size) {
				index++;
				if (objects[index] != null) {
					result = (T) objects[index];
					counter++;
					break;
				}
			}
			return result;
		}

		@Override
		public void remove() {
			removeAt(index);
			counter--;
		}
		
	}
	
	/**
	 * 转换为列表对象
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> toList() {
		List<T> list = new ArrayList<T>(size);
		for (Object obj : objects) {
			if (obj != null) {
				list.add((T) obj);
			}
		}
		return list;
	}
}
