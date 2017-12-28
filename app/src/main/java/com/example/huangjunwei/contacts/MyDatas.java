package com.example.huangjunwei.contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjunwei on 2017/12/22.
 */

public final class MyDatas {

    public static final List<PersonalData> PERSONAL_DATAS = new ArrayList<>();

    public static void addPersonalData(PersonalData argPersonalData) {

        int index = 0;
        boolean canUse;
        do {
            canUse = true;
            for (PersonalData personalData : PERSONAL_DATAS) {

                if (index == personalData.id) {

                    canUse = false;
                    index++;
                    break;
                }
            }
        } while (!canUse);
        argPersonalData.id = index;
        PERSONAL_DATAS.add(argPersonalData);
    }

    static public class PersonalData {

        public int id;
        public String name;
        public String telephone;
        public String note;


        public PersonalData() {

        }

        public PersonalData(int id, String name, String telephone, String note) {

            this.id = id;
            this.name = name;
            this.telephone = telephone;
            this.note = note;
        }
    }
}
