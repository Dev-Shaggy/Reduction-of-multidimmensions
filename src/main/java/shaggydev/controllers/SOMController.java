package shaggydev.controllers;

import shaggydev.interfaces.iController;

public class SOMController implements iController{

    private AppContoller appContoller;

    public void setUpController(AppContoller app){
        this.appContoller = app;
    }
}
