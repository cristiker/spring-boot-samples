package com.cristik.sample.log4j2.quartz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cristik
 */
public class ScheduleJobLog implements Serializable {
    private Long id;
    /**
     * 任务ID
     */
    private Integer scheduleJobId;
    /**
     * 执行任务的ip
     */
    private String ipAddress;
    private String remark;
    private Integer status;
    private String sts = "A";
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;

    public ScheduleJobLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Integer scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "ScheduleJobLog{" +
                "id=" + id +
                ", scheduleJobId=" + scheduleJobId +
                ", ipAddress='" + ipAddress + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", sts='" + sts + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
