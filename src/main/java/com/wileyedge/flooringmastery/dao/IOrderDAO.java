package com.wileyedge.flooringmastery.dao;

import java.util.Map;

import com.wileyedge.flooringmastery.dto.Order;

public interface IOrderDAO {
	Map<Integer,Order> readOrdersFromFile();
	void writeOrdersToFile(Map<Integer,Order> orderMap);
}
