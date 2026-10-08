module ir.qgdev.qoqnus.dmlink.domain.test {
    requires ir.qgdev.qoqnus.dmlink.domain;
    requires org.junit.jupiter.api;

    exports ir.qgdev.qoqnus.dmlink.domain.test.action to org.junit.platform.commons;
    exports ir.qgdev.qoqnus.dmlink.domain.test.role to org.junit.platform.commons;
    exports ir.qgdev.qoqnus.dmlink.domain.test.url to org.junit.platform.commons;
}