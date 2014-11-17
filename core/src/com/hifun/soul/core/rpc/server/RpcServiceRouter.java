package com.hifun.soul.core.rpc.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.google.protobuf.ServiceException;

/**
 * 服务路由器
 * <p>
 * 责任: 负责找到指定的服务,并且执行相应的方法;
 * </p>
 * 
 * @author crazyjohn
 * 
 */
@Component
public class RpcServiceRouter {
	/* 服务和服务名称的映射表 */
	private Map<String, Service> serviceNameMap = new HashMap<String, Service>();

	/**
	 * 注册服务
	 * 
	 * @param serviceImplementation
	 */
	public void registerService(Service serviceImplementation) {
		serviceNameMap.put(serviceImplementation.getDescriptorForType()
				.getName(), serviceImplementation);
	}

	/**
	 * 移除某项服务
	 * 
	 * @param serviceImplementation
	 */
	public void removeService(Service serviceImplementation) {
		if (serviceNameMap.containsKey(serviceImplementation
				.getDescriptorForType().getName())) {
			serviceNameMap.remove(serviceImplementation.getDescriptorForType()
					.getName());
		}
	}

	/**
	 * 根据指定服务名称获取某项服务
	 * 
	 * @param serviceName
	 *            服务名称
	 * @return null 当没有注册此服务;返回指定的服务
	 */
	public Service getService(String serviceName) {
		return serviceNameMap.get(serviceName);
	}

	/**
	 * 找到指定的服务并且执行相应的方法
	 * 
	 * @param serviceIndentifer
	 *            服务标识
	 * @param methodIndentifer
	 *            方法标识
	 * @param parameterBytes
	 *            参数数据
	 * @return 返回方法执行后的返回值
	 * @throws ServiceException
	 * @throws InvalidProtocolBufferException
	 *             protobuf解析异常
	 */
	public void findServiceAndExecuteMethod(String serviceIndentifer,
			String methodIndentifer, byte[] parameterBytes,
			RpcCallback<Message> callback) throws ServiceException,
			InvalidProtocolBufferException {
		Service service = this.getService(serviceIndentifer);
		if (service == null) {
			throw new NullPointerException("Unknown service: "
					+ serviceIndentifer);
		}
		MethodDescriptor method = service.getDescriptorForType()
				.findMethodByName(methodIndentifer);
		if (method == null) {
			throw new NullPointerException("Unknown method: "
					+ methodIndentifer);
		}
		Message requestProtoType = service.getRequestPrototype(method);
		Message request = null;
		if (parameterBytes != null) {
			request = requestProtoType.newBuilderForType()
					.mergeFrom(parameterBytes).build();
		}
		try {
			service.callMethod(method, null, request, callback);
		} catch (Exception e) {
			throw new ServiceException("Call method error, info : "
					+ e);
		}
	}

}
