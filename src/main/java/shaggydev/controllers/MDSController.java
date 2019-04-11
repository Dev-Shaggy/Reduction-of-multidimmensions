package shaggydev.controllers;

import shaggydev.interfaces.iController;

public class MDSController implements iController{

    private AppContoller appContoller;


    public void setUpController(AppContoller app){
        this.appContoller = app;
    }
}
