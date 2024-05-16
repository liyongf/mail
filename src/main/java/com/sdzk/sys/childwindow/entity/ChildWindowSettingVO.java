package com.sdzk.sys.childwindow.entity;

public class ChildWindowSettingVO {
    private TSChildWindowsEntity leftTop;
    private TSChildWindowsEntity centerTop;
    private TSChildWindowsEntity rightTop;
    private TSChildWindowsEntity leftBottom;
    private TSChildWindowsEntity centerBottom;
    private TSChildWindowsEntity rightBottom;

    public TSChildWindowsEntity getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(TSChildWindowsEntity leftTop) {
        this.leftTop = leftTop;
    }

    public TSChildWindowsEntity getCenterTop() {
        return centerTop;
    }

    public void setCenterTop(TSChildWindowsEntity centerTop) {
        this.centerTop = centerTop;
    }

    public TSChildWindowsEntity getRightTop() {
        return rightTop;
    }

    public void setRightTop(TSChildWindowsEntity rightTop) {
        this.rightTop = rightTop;
    }

    public TSChildWindowsEntity getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(TSChildWindowsEntity leftBottom) {
        this.leftBottom = leftBottom;
    }

    public TSChildWindowsEntity getCenterBottom() {
        return centerBottom;
    }

    public void setCenterBottom(TSChildWindowsEntity centerBottom) {
        this.centerBottom = centerBottom;
    }

    public TSChildWindowsEntity getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(TSChildWindowsEntity rightBottom) {
        this.rightBottom = rightBottom;
    }

    public boolean isComplete() {
        if (leftTop == null || centerTop == null || rightTop == null || leftBottom == null || centerBottom == null || rightBottom == null) {
            return false;
        } else {
            return true;
        }
    }
}
