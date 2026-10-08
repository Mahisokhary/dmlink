package ir.qgdev.qoqnus.dmlink.domain.test.action;

import ir.qgdev.qoqnus.dmlink.domain.action.Action;
import ir.qgdev.qoqnus.dmlink.domain.action.SimpleAction;

public final class ActionConstants {
    public static final Action ACTION_IMPORTANT = new SimpleAction("important");
    public static final Action ACTION_VERY_IMPORTANT = new SimpleAction("important.very");
    public static final Action ACTION_NOT_IMPORTANT = new SimpleAction("important.not");

}
