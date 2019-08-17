package com.wqh.hummingbird.server.service;

import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceVO;

public interface IDeviceService {

    Response<String> addDevice(DeviceAddBO r);

    Response<String> deleteById(Long id);

    LayuiPage<DeviceVO> getPage(BasePage b);
}
