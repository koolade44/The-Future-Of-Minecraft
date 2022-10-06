package com.koolade446.tfmc.client;

public class CustomPlayerValues {

    private float portalCoolDown;
    private boolean isInPortal;

    public CustomPlayerValues() {
        this.portalCoolDown = 0;
        this.isInPortal = false;
    }

    public void setPortalCoolDown(float coolDownTime) {
        this.portalCoolDown = coolDownTime;
    }

    public boolean isOnPortalCoolDown() {
        return System.currentTimeMillis() >= System.currentTimeMillis() + portalCoolDown;
    }

    public float getPortalCoolDown() {
        return portalCoolDown;
    }

    public void setInPortal(boolean inPortal) {
        this.isInPortal = inPortal;
    }

    public boolean isInPortal() {
        return isInPortal;
    }
}
