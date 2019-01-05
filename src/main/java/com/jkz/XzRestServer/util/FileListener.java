package com.jkz.XzRestServer.util;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by scuhmz on 2017/12/16.
 */
@Component
public class FileListener implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor start scan files..");
    }


    @Override
    public void onDirectoryCreate(File file) {
        System.out.println(file.getName()+" director created.");
    }


    @Override
    public void onDirectoryChange(File file) {
        System.out.println(file.getName()+" director changed.");
    }


    @Override
    public void onDirectoryDelete(File file) {
        System.out.println(file.getName()+" director deleted.");
    }


    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getName()+" created.");
    }


    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName()+" changed.");
    }


    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName()+" deleted.");
    }


    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor stop scanning..");
    }
}
