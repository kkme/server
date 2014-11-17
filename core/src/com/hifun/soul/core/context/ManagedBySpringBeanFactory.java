package com.hifun.soul.core.context;

/**
 * 托管给spring的bean的工厂;
 * 
 * @author crazyjohn
 * 
 */
public class ManagedBySpringBeanFactory {

	/**
	 * 根据Bean类型获取Bean;
	 * <p>
	 * 前提是此bean已经托管给了spring, 也就是bean类中添加了@Component标记;
	 * 
	 * @param beanClassType
	 *            对应的class类型;
	 * @return 如果此类没有托管会返回null;
	 */
	public <Bean> Bean createBeanByClassType(Class<Bean> beanClassType) {
		return ApplicationContext.getApplicationContext()
				.getBean(beanClassType);
	}
}
