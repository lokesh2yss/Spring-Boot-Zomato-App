package com.codingshuttle.app.zomatoApp.json;

import java.util.List;

public class OpenCageGeocodingResponse {
    private String documentation;
    private List<License> licenses;
    private Rate rate;
    private List<Result> results;
    private Status status;
    private StayInformed stay_informed;
    private String thanks;
    private Timestamp timestamp;
    private int total_results;

    // Getters and Setters
    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StayInformed getStay_informed() {
        return stay_informed;
    }

    public void setStay_informed(StayInformed stay_informed) {
        this.stay_informed = stay_informed;
    }

    public String getThanks() {
        return thanks;
    }

    public void setThanks(String thanks) {
        this.thanks = thanks;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public static class License {
        private String name;
        private String url;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Rate {
        private int limit;
        private int remaining;
        private long reset;

        // Getters and Setters
        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getRemaining() {
            return remaining;
        }

        public void setRemaining(int remaining) {
            this.remaining = remaining;
        }

        public long getReset() {
            return reset;
        }

        public void setReset(long reset) {
            this.reset = reset;
        }
    }

    public static class Result {
        private Annotations annotations;
        private Components components;
        private int confidence;
        private String formatted;
        private Geometry geometry;

        // Getters and Setters
        public Annotations getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Annotations annotations) {
            this.annotations = annotations;
        }

        public Components getComponents() {
            return components;
        }

        public void setComponents(Components components) {
            this.components = components;
        }

        public int getConfidence() {
            return confidence;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }

        public String getFormatted() {
            return formatted;
        }

        public void setFormatted(String formatted) {
            this.formatted = formatted;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public static class Annotations {
            private DMS DMS;
            private String MGRS;
            private String Maidenhead;
            private Mercator Mercator;
            private OSM OSM;
            private UN_M49 UN_M49;
            private int callingcode;
            private Currency currency;
            private String flag;
            private String geohash;
            private double qibla;
            private Roadinfo roadinfo;
            private Sun sun;
            private Timezone timezone;
            private What3words what3words;

            // Getters and Setters
            public DMS getDMS() {
                return DMS;
            }

            public void setDMS(DMS DMS) {
                this.DMS = DMS;
            }

            public String getMGRS() {
                return MGRS;
            }

            public void setMGRS(String MGRS) {
                this.MGRS = MGRS;
            }

            public String getMaidenhead() {
                return Maidenhead;
            }

            public void setMaidenhead(String Maidenhead) {
                this.Maidenhead = Maidenhead;
            }

            public Mercator getMercator() {
                return Mercator;
            }

            public void setMercator(Mercator Mercator) {
                this.Mercator = Mercator;
            }

            public OSM getOSM() {
                return OSM;
            }

            public void setOSM(OSM OSM) {
                this.OSM = OSM;
            }

            public UN_M49 getUN_M49() {
                return UN_M49;
            }

            public void setUN_M49(UN_M49 UN_M49) {
                this.UN_M49 = UN_M49;
            }

            public int getCallingcode() {
                return callingcode;
            }

            public void setCallingcode(int callingcode) {
                this.callingcode = callingcode;
            }

            public Currency getCurrency() {
                return currency;
            }

            public void setCurrency(Currency currency) {
                this.currency = currency;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getGeohash() {
                return geohash;
            }

            public void setGeohash(String geohash) {
                this.geohash = geohash;
            }

            public double getQibla() {
                return qibla;
            }

            public void setQibla(double qibla) {
                this.qibla = qibla;
            }

            public Roadinfo getRoadinfo() {
                return roadinfo;
            }

            public void setRoadinfo(Roadinfo roadinfo) {
                this.roadinfo = roadinfo;
            }

            public Sun getSun() {
                return sun;
            }

            public void setSun(Sun sun) {
                this.sun = sun;
            }

            public Timezone getTimezone() {
                return timezone;
            }

            public void setTimezone(Timezone timezone) {
                this.timezone = timezone;
            }

            public What3words getWhat3words() {
                return what3words;
            }

            public void setWhat3words(What3words what3words) {
                this.what3words = what3words;
            }

            public static class DMS {
                private String lat;
                private String lng;

                // Getters and Setters
                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }

            public static class Mercator {
                private double x;
                private double y;

                // Getters and Setters
                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }

            public static class OSM {
                private String note_url;
                private String url;

                // Getters and Setters
                public String getNote_url() {
                    return note_url;
                }

                public void setNote_url(String note_url) {
                    this.note_url = note_url;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class UN_M49 {
                private Regions regions;
                private List<String> statistical_groupings;

                // Getters and Setters
                public Regions getRegions() {
                    return regions;
                }

                public void setRegions(Regions regions) {
                    this.regions = regions;
                }

                public List<String> getStatistical_groupings() {
                    return statistical_groupings;
                }

                public void setStatistical_groupings(List<String> statistical_groupings) {
                    this.statistical_groupings = statistical_groupings;
                }

                public static class Regions {
                    private String ASIA;
                    private String IN;
                    private String SOUTHERN_ASIA;
                    private String WORLD;

                    // Getters and Setters
                    public String getASIA() {
                        return ASIA;
                    }

                    public void setASIA(String ASIA) {
                        this.ASIA = ASIA;
                    }

                    public String getIN() {
                        return IN;
                    }

                    public void setIN(String IN) {
                        this.IN = IN;
                    }

                    public String getSOUTHERN_ASIA() {
                        return SOUTHERN_ASIA;
                    }

                    public void setSOUTHERN_ASIA(String SOUTHERN_ASIA) {
                        this.SOUTHERN_ASIA = SOUTHERN_ASIA;
                    }

                    public String getWORLD() {
                        return WORLD;
                    }

                    public void setWORLD(String WORLD) {
                        this.WORLD = WORLD;
                    }
                }
            }

            public static class Currency {
                private List<String> alternate_symbols;
                private String decimal_mark;
                private String html_entity;
                private String iso_code;
                private String iso_numeric;
                private String name;
                private int smallest_denomination;
                private String subunit;
                private int subunit_to_unit;
                private String symbol;
                private int symbol_first;
                private String thousands_separator;

                // Getters and Setters
                public List<String> getAlternate_symbols() {
                    return alternate_symbols;
                }

                public void setAlternate_symbols(List<String> alternate_symbols) {
                    this.alternate_symbols = alternate_symbols;
                }

                public String getDecimal_mark() {
                    return decimal_mark;
                }

                public void setDecimal_mark(String decimal_mark) {
                    this.decimal_mark = decimal_mark;
                }

                public String getHtml_entity() {
                    return html_entity;
                }

                public void setHtml_entity(String html_entity) {
                    this.html_entity = html_entity;
                }

                public String getIso_code() {
                    return iso_code;
                }

                public void setIso_code(String iso_code) {
                    this.iso_code = iso_code;
                }

                public String getIso_numeric() {
                    return iso_numeric;
                }

                public void setIso_numeric(String iso_numeric) {
                    this.iso_numeric = iso_numeric;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSmallest_denomination() {
                    return smallest_denomination;
                }

                public void setSmallest_denomination(int smallest_denomination) {
                    this.smallest_denomination = smallest_denomination;
                }

                public String getSubunit() {
                    return subunit;
                }

                public void setSubunit(String subunit) {
                    this.subunit = subunit;
                }

                public int getSubunit_to_unit() {
                    return subunit_to_unit;
                }

                public void setSubunit_to_unit(int subunit_to_unit) {
                    this.subunit_to_unit = subunit_to_unit;
                }

                public String getSymbol() {
                    return symbol;
                }

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public int getSymbol_first() {
                    return symbol_first;
                }

                public void setSymbol_first(int symbol_first) {
                    this.symbol_first = symbol_first;
                }

                public String getThousands_separator() {
                    return thousands_separator;
                }

                public void setThousands_separator(String thousands_separator) {
                    this.thousands_separator = thousands_separator;
                }
            }

            public static class Roadinfo {
                private String drive_on;
                private String speed_in;

                // Getters and Setters
                public String getDrive_on() {
                    return drive_on;
                }

                public void setDrive_on(String drive_on) {
                    this.drive_on = drive_on;
                }

                public String getSpeed_in() {
                    return speed_in;
                }

                public void setSpeed_in(String speed_in) {
                    this.speed_in = speed_in;
                }
            }

            public static class Sun {
                private SunriseSet rise;
                private SunriseSet set;

                // Getters and Setters
                public SunriseSet getRise() {
                    return rise;
                }

                public void setRise(SunriseSet rise) {
                    this.rise = rise;
                }

                public SunriseSet getSet() {
                    return set;
                }

                public void setSet(SunriseSet set) {
                    this.set = set;
                }

                public static class SunriseSet {
                    private long apparent;
                    private long astronomical;
                    private long civil;
                    private long nautical;

                    // Getters and Setters
                    public long getApparent() {
                        return apparent;
                    }

                    public void setApparent(long apparent) {
                        this.apparent = apparent;
                    }

                    public long getAstronomical() {
                        return astronomical;
                    }

                    public void setAstronomical(long astronomical) {
                        this.astronomical = astronomical;
                    }

                    public long getCivil() {
                        return civil;
                    }

                    public void setCivil(long civil) {
                        this.civil = civil;
                    }

                    public long getNautical() {
                        return nautical;
                    }

                    public void setNautical(long nautical) {
                        this.nautical = nautical;
                    }
                }
            }

            public static class Timezone {
                private String name;
                private int now_in_dst;
                private int offset_sec;
                private String offset_string;
                private String short_name;

                // Getters and Setters
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getNow_in_dst() {
                    return now_in_dst;
                }

                public void setNow_in_dst(int now_in_dst) {
                    this.now_in_dst = now_in_dst;
                }

                public int getOffset_sec() {
                    return offset_sec;
                }

                public void setOffset_sec(int offset_sec) {
                    this.offset_sec = offset_sec;
                }

                public String getOffset_string() {
                    return offset_string;
                }

                public void setOffset_string(String offset_string) {
                    this.offset_string = offset_string;
                }

                public String getShort_name() {
                    return short_name;
                }

                public void setShort_name(String short_name) {
                    this.short_name = short_name;
                }
            }

            public static class What3words {
                private String words;

                // Getters and Setters
                public String getWords() {
                    return words;
                }

                public void setWords(String words) {
                    this.words = words;
                }
            }
        }

        public static class Components {
            private String ISO_3166_1_alpha_2;
            private String ISO_3166_1_alpha_3;
            private String _category;
            private String _type;
            private String continent;
            private String country;
            private String country_code;
            private String postcode;
            private String state;

            // Getters and Setters
            public String getISO_3166_1_alpha_2() {
                return ISO_3166_1_alpha_2;
            }

            public void setISO_3166_1_alpha_2(String ISO_3166_1_alpha_2) {
                this.ISO_3166_1_alpha_2 = ISO_3166_1_alpha_2;
            }

            public String getISO_3166_1_alpha_3() {
                return ISO_3166_1_alpha_3;
            }

            public void setISO_3166_1_alpha_3(String ISO_3166_1_alpha_3) {
                this.ISO_3166_1_alpha_3 = ISO_3166_1_alpha_3;
            }

            public String get_category() {
                return _category;
            }

            public void set_category(String _category) {
                this._category = _category;
            }

            public String get_type() {
                return _type;
            }

            public void set_type(String _type) {
                this._type = _type;
            }

            public String getContinent() {
                return continent;
            }

            public void setContinent(String continent) {
                this.continent = continent;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        public static class Geometry {
            private double lat;
            private double lng;

            // Getters and Setters
            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }

    public static class Status {
        private int code;
        private String message;

        // Getters and Setters
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class StayInformed {
        private String blog;
        private String mastodon;

        // Getters and Setters
        public String getBlog() {
            return blog;
        }

        public void setBlog(String blog) {
            this.blog = blog;
        }

        public String getMastodon() {
            return mastodon;
        }

        public void setMastodon(String mastodon) {
            this.mastodon = mastodon;
        }
    }

    public static class Timestamp {
        private String created_http;
        private long created_unix;

        // Getters and Setters
        public String getCreated_http() {
            return created_http;
        }

        public void setCreated_http(String created_http) {
            this.created_http = created_http;
        }

        public long getCreated_unix() {
            return created_unix;
        }

        public void setCreated_unix(long created_unix) {
            this.created_unix = created_unix;
        }
    }
}
