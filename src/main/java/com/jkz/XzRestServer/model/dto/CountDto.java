package com.jkz.XzRestServer.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by scuhmz on 2017/12/2.
 */
@Accessors(chain = true)
@Data
public class CountDto {

    @ApiModelProperty(notes = "xxxx年xx月")
    private String year_and_month;
    @ApiModelProperty(notes = "架次类型")
    private String flightFx_Hx_DM;
    @ApiModelProperty(notes = "次数统计")
    private String flightFx_Hx_DM_count;
}
