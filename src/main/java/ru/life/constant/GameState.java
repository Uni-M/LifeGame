package ru.life.constant;

public enum GameState {

    LIFE {
        @Override
        public GameState nextState() {
            return PAUSE;
        }
    },
    CLEAN {
        @Override
        public GameState nextState() {
            return PAUSE;
        }
    },
    STEP {
        @Override
        public GameState nextState() {
            return PAUSE;
        }
    },
    PAUSE {
        @Override
        public GameState nextState() {
            return LIFE;
        }
    },
    RESIZE {
        @Override
        public GameState nextState() {
            return LIFE;
        }
    };

    public abstract GameState nextState();

}
