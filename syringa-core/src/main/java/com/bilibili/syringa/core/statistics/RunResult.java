/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.time.LocalDateTime;

/**
 *
 * @author xuezhaoming
 * @version $Id: RunResult.java, v 0.1 2019-01-15 2:41 PM Exp $$
 */
public class RunResult {

    private boolean       success;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private long          sizePer;
    private long          message;
    private long          totalSize;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public long getSizePer() {
        return sizePer;
    }

    public void setSizePer(long sizePer) {
        this.sizePer = sizePer;
    }

    public long getMessage() {
        return message;
    }

    public void setMessage(long message) {
        this.message = message;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
