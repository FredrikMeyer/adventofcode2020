(ns adventofcode2020.day4
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [spec-coerce.core :as sc]))

(def current-ns-name (str (ns-name *ns*)))

(defn parse-height [height-string]
  (let [regex #"^(?:([0-9]+)cm)|(?:([0-9]+)in)$"
        matches (re-matches regex height-string)]
    (if matches
      (let [[_ cm in] matches]
        (if cm
          { :value (read-string cm) :unit :cm
           }
          { :value (read-string in) :unit :in
           }))
      nil)))

(s/def ::byr (s/int-in 1920 2003))
(s/def ::iyr (s/int-in 2010 2021))
(s/def ::eyr (s/int-in 2020 2031))
(s/def ::hgt
  (s/and string?
         (fn [s] (let [parsed (parse-height s)]
                   (if parsed
                     (or (and (= (:unit parsed) :cm) (<= 150 (:value parsed) 193))
                         (and (= (:unit parsed) :in) (<= 59 (:value parsed) 76)))
                     false)))))
(s/def ::hcl (s/and string? #(re-matches #"^#[a-f0-9]{6}$" %)))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::pid (s/and string? #(re-matches #"^[0-9]{9}$" %)))
(s/def ::cid string?)

(s/def ::passport
  (s/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
          :opt [::cid]))

(defn parse-passport [raw-passport]
  (let [splitted (str/split raw-passport #"\s")]
    (->> splitted
     (map (fn [entry] (str/split entry #":")))
     (map (fn [[k v]] [(keyword current-ns-name k) v]))
     (into {})
     (sc/coerce-structure))))

(defn parse-data [data]
  (let [raw-passports (str/split data #"\n\n")]
    (map parse-passport raw-passports)))

(def required-fields [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid])
(def optional-field [::cid])

(defn passport-contains-all-required-fields [passport]
  (every? (fn [f] (contains? passport f)) required-fields))


(defn day4-part-1 [data]
  (->> data
       (parse-data)
       (filter passport-contains-all-required-fields)
       (count)))

(defn day4-part-2 [data]
  (->> data
       (parse-data)
       (filter (fn [p] (s/valid? ::passport p)))
       (count)))


;;;; Test data

(def test-data "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648")

(def test-invalid "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007")

(def test-valid "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719")

