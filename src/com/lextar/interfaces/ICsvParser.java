package com.lextar.interfaces;

import java.io.IOException;
import java.util.List;

public interface ICsvParser<T> {
	//public <T> List<T> Prase(String path, ICsvData beanClass);
	public T Parse(String path) throws IOException;
	
}
