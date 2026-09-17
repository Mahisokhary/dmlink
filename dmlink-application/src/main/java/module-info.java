module ir.qgdev.qoqnus.dmlink.application {
    requires ir.qgdev.qoqnus.dmlink.domain;
    requires static lombok;

    exports ir.qgdev.qoqnus.dmlink.application;
    exports ir.qgdev.qoqnus.dmlink.application.crypto;
    exports ir.qgdev.qoqnus.dmlink.application.role;
    exports ir.qgdev.qoqnus.dmlink.application.user;
    exports ir.qgdev.qoqnus.dmlink.application.session;
    exports ir.qgdev.qoqnus.dmlink.application.link;
}