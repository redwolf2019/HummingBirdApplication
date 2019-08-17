package com.wqh.hummingbird.server.service;

import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.DataPointAddBO;
import com.wqh.hummingbird.server.common.bo.DataPointPageBO;
import com.wqh.hummingbird.server.common.vo.DataPointVO;

public interface IDataPointService {


    LayuiPage<DataPointVO> getPage(DataPointPageBO r);

    Response<String> addDataPoint(DataPointAddBO r);

    Response<String> deleteDataPointById(Long id);

    Response<String> deleteDataPointByDeviceTypeId(Integer id);


}
