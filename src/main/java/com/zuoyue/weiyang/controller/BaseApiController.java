package com.zuoyue.weiyang.controller;

import com.zuoyue.weiyang.exception.BadRequestException;
import com.zuoyue.weiyang.exception.CurrentUserException;
import com.zuoyue.weiyang.exception.FileSaveException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseApiController {

	// 运行时异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> runtimeExceptionHandler(RuntimeException ex) {
		ex.printStackTrace();
		return onResponse(500, "运行时异常");
	}
	
	// 空指针异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> nullPointExceptionHandler(NullPointerException ex) {
		ex.printStackTrace();
		return onResponse(500, "空指针异常");
	}
	
	// 类型转换异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ClassCastException.class)
	public Map<String, Object> classCastExceptionHandler(ClassCastException ex) {
		ex.printStackTrace();
		return onResponse(500, "空指针异常");
	}
	
	// IO异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Object> iOException(IOException ex) {
		ex.printStackTrace();
		return new ResponseEntity<Object>(onResponse(500, "IO异常"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 未知方法异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NoSuchMethodException.class)
	public Map<String, Object> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		ex.printStackTrace();
		return onResponse(500, "未知方法异常");
	}
	
	// 数组越界异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public Map<String, Object> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		ex.printStackTrace();
		return onResponse(500, "数组越界异常");
	}
	
	// 400错误
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, Object> requestNotReadble(HttpMessageNotReadableException ex) {
		return onResponse(400, ex.getMessage());
	}
	
	// 400错误
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TypeMismatchException.class)
	public Map<String, Object> requestTypeMismatch(TypeMismatchException ex) {
		return onResponse(400, ex.getMessage());
	}
	
	// 400错误
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Map<String, Object> requestMissingServletRequest(MissingServletRequestParameterException ex) {
		return onResponse(400, ex.getMessage());
	}
	
	// 405错误
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Map<String, Object> request405()
	{
		return onResponse(405, "");
	}
	
	// 406错误
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public Map<String, Object> request406()
	{
		return onResponse(406, "");
	}
	
	// 500错误
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMessageNotWritableException.class})
	public Map<String, Object> server500(Exception ex) {
		ex.printStackTrace();
		return onResponse(500, ex.getMessage());
	}
	
	/**
	 * 全局处理Exception
	 * 错误的情况返回500
	 *
	 * @param ex
	 * @param req
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, Object> otherExceptionHandler(Exception ex, WebRequest req) {
		ex.printStackTrace();
		return onResponse(500, ex.getMessage());
	}
	
	// bad请求
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public Map<String, Object> badRequestHandler(BadRequestException ex) {
		return onResponse(ex.getCode(), ex.getMessage());
	}

	// 文件储存失败
	@ExceptionHandler(FileSaveException.class)
	public Map<String, Object> fileSaveExceptionHandler(FileSaveException ex) {
		ex.printStackTrace();
		return onResponse(400, "上传文件失败");
	}

//	// 没有认证
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(UnauthenticatedException.class)
//	public Map<String, Object> unauthenticated(Exception ex) {
//		return onResponse(CustomHttpStatus.UNPERM);
//	}
//
//	// shiro角色权限异常
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(UnauthorizedException.class)
//	public Map<String, Object> unauthorizedExceptionHandler(UnauthorizedException ex) {
//		return onResponse(CustomHttpStatus.UNPERM);
//	}

//	// 用户登录信息session异常
//	@ExceptionHandler(CurrentUserException.class)
//	public Map<String, Object> currentUserExceptionHandler(CurrentUserException ex) {
//		return onResponse(CustomHttpStatus.USER_MSG_EXCEPTION.getCode(), ex.getMessage());
//	}

	protected boolean isBlankForUpdate(String str) {
		if (str != null && str.trim().length() == 0) return true;
		return false;
	}

	protected boolean isBlankForUpdate(Integer id) {
		if (id != null && id < 0) return true;
		return false;
	}

	protected boolean isBlankForUpdate(Long id) {
		if (id != null && id < 0) return true;
		return false;
	}

	protected Map<String, Object> onResponse(int code, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		if (msg != null) map.put("msg", msg);
		return map;
	}

	protected Map<String, Object> onResponses(int code, Object msg,Object status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		if (msg != null) map.put("msg", msg);
		map.put("status", status);
		return map;
	}

	protected Map<String, Object> onSuccessReps(Object msg,Object status) {
		return onResponses(200, msg,status);
	}

	protected Map<String, Object> onBadResps(Object msg,Object status) {
		return onResponses(400, msg,status);
	}

//	protected Map<String, Object> onResponse(CustomHttpStatus status) {
//		return onResponse(status.getCode(), status.getDesc());
//	}

	protected Map<String, Object> onSuccessRep(String msg) {
		return onResponse(200, msg);
	}

	protected Map<String, Object> onBadResp(String msg) {
		return onResponse(400, msg);
	}

	protected Map<String, Object> onRespWithId(String msg, Long id) {
		Map<String, Object> map = onSuccessRep(msg);
		if (id != null) map.put("id", id);
		return map;
	}

	protected Map<String, Object> onDataResp(int code, String msg, Object data) {
		Map<String, Object> map = onResponse(code, msg);
		map.put("data", data);
		return map;
	}

	protected Map<String, Object> onDataResp(Object data) {
		return onDataResp(200, null, data);
	}
}
