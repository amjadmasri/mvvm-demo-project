package com.classic.mvvmapplication.data.models.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "countries", indices = {@Index(value = "id", unique = true)})
public class Country {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("altSpellings")
    @Expose
    private List<String> altSpellings = null;
    @SerializedName("relevance")
    @Expose
    private String relevance;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("latlng")
    @Expose
    private List<Integer> latlng = null;
    @SerializedName("demonym")
    @Expose
    private String demonym;
    @SerializedName("area")
    @Expose
    private Integer area;
    @SerializedName("gini")
    @Expose
    private Integer gini;
    @SerializedName("timezones")
    @Expose
    private List<String> timezones = null;
    @SerializedName("callingCodes")
    @Expose
    private List<String> callingCodes = null;
    @SerializedName("topLevelDomain")
    @Expose
    private List<String> topLevelDomain = null;
    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;
    @SerializedName("currencies")
    @Expose
    private List<String> currencies = null;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
}
