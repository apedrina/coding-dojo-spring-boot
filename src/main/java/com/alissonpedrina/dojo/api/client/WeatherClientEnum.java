package com.alissonpedrina.dojo.api.client;

public enum WeatherClientEnum {

    PARAM_KEY {
        @Override
        public String toString() {
            return "appid";

        }
    },
    PARAM_BBOX {
        @Override
        public String toString() {
            return "bbox";

        }
    },
    PARAM_LONG {
        @Override
        public String toString() {
            return "lon";

        }
    },
    PARAM_LAT {
        @Override
        public String toString() {
            return "lat";

        }
    },
    PARAM_ZIP {
        @Override
        public String toString() {
            return "zip";

        }
    },
    PARAM_ID {
        @Override
        public String toString() {
            return "id";

        }
    },
    PARAM_Q {
        @Override
        public String toString() {
            return "q";

        }
    },
    WEATHER_ENDPOINT {
        @Override
        public String toString() {
            return "weather";

        }
    },
    WEATHER_ENDPOINT_BOX_CITY {
        @Override
        public String toString() {
            return "box/city";

        }
    }

}
