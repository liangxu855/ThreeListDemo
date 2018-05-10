package com.example.administrator.threelistdemo.model;

import java.util.List;

public class PlayDataBean {

    private List<Play_list> play_list;

    public void setPlay_list(List<Play_list> play_list) {
        this.play_list = play_list;
    }

    public List<Play_list> getPlay_list() {
        return this.play_list;
    }

    public class Play_list {
        private String name;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }
}
