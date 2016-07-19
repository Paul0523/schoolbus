package com.schoolbus.service;

import com.schoolbus.entity.Customer;

/**
 * Created by Paul on 2016/3/2.
 */
public interface CustomerService {
    public String getStation(Customer customer);
    public String getSearch(Customer customer);
    public String getLine(Customer customer);
    public String getMapLine(Customer customer);
    public String getLineStations(Customer customer);
    public String getLineBuses(Customer customer);
    public String callBus(Customer customer);
}
