package com.android.marco.tellme.utils;

/**
 * Created by gen on 15.04.15.
 */
public class SharedPrefKeys {

    public static final String BASE_KEY = "com.tell.me.prefs";

    public static class ScrollPosition {
        public static final String TAB_1 = BASE_KEY + "scroll.tab.1";
        public static final String TAB_2 = BASE_KEY + "scroll.tab.2";
    }

    public static class SelectedTab {
        public static final String TAB_SELECTED_INDEX = BASE_KEY + "tab.position";
    }

    public static class SessionLanguages {
        public static final String LANG_1 = BASE_KEY + "row.1";
        public static final String LANG_2 = BASE_KEY + "row.2";
    }

    public static class WordList {
        public static final String KEEP_WORD_LIST = BASE_KEY + "word.list";
    }

    public static class KeyboardMode {
        public static final String KEEP_KEYBOARD = BASE_KEY + "keyboard.mode.show.always";
    }

    public static class AutoSpeak {
        public static final String SPEAK = BASE_KEY + "auto.speak";
    }

    public static class FlagMode {
        public static final String SHOW_FLAGS = BASE_KEY + "flag.mode";
    }

}
