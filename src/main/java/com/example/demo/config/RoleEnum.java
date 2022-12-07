//package com.example.demo.config;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Arrays;
//import java.util.List;
//
//    public enum RoleEnum implements GrantedAuthority {
//        User("Покупатель", true),
//        Admin("Администратор", true),
//        Employee("Сотрудник склада", true);
//        private final String displayedName;
//        private final Boolean position;
//
//        public final static List<RoleEnum> positions = Arrays.stream(RoleEnum.values()).filter(roleEnum -> roleEnum.position).toList();
//
//        RoleEnum(String name, Boolean position) {
//            this.position = position;
//            this.displayedName = name;
//        }
//
//        public String getDisplayedName() {
//            return displayedName;
//        }
//
//        public Boolean getPosition() {
//            return position;
//        }
//
//        @Override
//        public String getAuthority() {
//            return this.name();
//        }
//
//    }