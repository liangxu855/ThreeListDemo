package com.example.administrator.threelistdemo.model;

import java.util.List;

public class RoleDataBean {

    private List<Time_list> time_list;

    public void setTime_list(List<Time_list> time_list) {
        this.time_list = time_list;
    }

    public List<Time_list> getTime_list() {
        return this.time_list;
    }

    public class Time_list {
        private String time;

        private List<Role_list> role_list;

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return this.time;
        }

        public void setRole_list(List<Role_list> role_list) {
            this.role_list = role_list;
        }

        public List<Role_list> getRole_list() {
            return this.role_list;
        }

    }

    public class Role_list {
        private String role;

        private String state;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRole() {
            return this.role;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return this.state;
        }

    }
}
