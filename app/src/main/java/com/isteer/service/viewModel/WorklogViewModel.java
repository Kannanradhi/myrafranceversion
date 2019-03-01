package com.isteer.service.viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.isteer.service.app.App;
import com.isteer.service.model.SpareItemData;
import com.isteer.service.model.WorkLogData;

import java.util.ArrayList;

public class WorklogViewModel extends ViewModel {



    public WorklogViewModel() {

    }


    public void syncWorklog(ArrayList<WorkLogData> w) {
        for (WorkLogData d : w) {
            if (!App.getINSTANCE().getRoomDB().daoWorkLog().isOldEntry("" + d.getScl_key())) {
                long inseted = App.getINSTANCE().getRoomDB().daoWorkLog().insertWorkLogData(d);
            } else {
                int updated = App.getINSTANCE().getRoomDB().daoWorkLog().updateWorkLogData(d);
            }
        }
    }

    public void syncSpareParts(ArrayList<SpareItemData> s) {
        for (SpareItemData d : s) {
            if (!App.getINSTANCE().getRoomDB().daoSpareItem().isOldEntry("" + d.getScsp_key())) {
                long inseted = App.getINSTANCE().getRoomDB().daoSpareItem().insertOnlySingleRecord(d);
            } else {
                int updated = App.getINSTANCE().getRoomDB().daoSpareItem().UpdateSparePartes(d);
            }
        }
    }


}
