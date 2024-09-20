module moduletwo {
    requires transitive moduleone; // supply dependencies to any other module using/requiring module2

    exports com.moduletwo.dtos;
    exports com.moduletwo.service;
}