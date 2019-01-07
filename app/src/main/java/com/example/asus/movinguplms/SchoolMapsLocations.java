package com.example.asus.movinguplms;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class SchoolMapsLocations {

    private GoogleMap googleMap;
    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private double latitide, longitude;
    private int ProximityRadius = 10000;
    private GoogleApiClient googleApiClient;

    public static double latitude1 = 14.600363;
    public static double longitude1 = 120.996021;
    public static LatLng locschool1 = new LatLng(SchoolMapsLocations.latitude1, SchoolMapsLocations.longitude1);
    public static String title1 = "ABE International Business College, Manila Campus, Legarda Street, Sampaloc, Manila, Metro Manila";

    public static double latitude2 = 14.6027894;
    public static double longitude2 = 120.9830819;
    public static LatLng locschool2 = new LatLng(SchoolMapsLocations.latitude2, SchoolMapsLocations.longitude2);
    public static String title2 = "ACCESS Computer College";

    public static double latitude3 = 14.5891708;
    public static double longitude3 = 120.9842066;
    public static LatLng locschool3 = new LatLng(SchoolMapsLocations.latitude3, SchoolMapsLocations.longitude3);
    public static String title3 = "Adamson University";

    public static double latitude4 = 14.5821999;
    public static double longitude4 = 120.9844762;
    public static LatLng locschool4 = new LatLng(SchoolMapsLocations.latitude4, SchoolMapsLocations.longitude4);
    public static String title4 = "APEC Schools – Emilio Aguinaldo College";

    public static double latitude5 = 14.599903;
    public static double longitude5 = 120.9942845;
    public static LatLng locschool5 = new LatLng(SchoolMapsLocations.latitude5, SchoolMapsLocations.longitude5);
    public static String title5 = "Arellano University";

    public static double latitude6 = 14.5828469;
    public static double longitude6 = 120.9831985;
    public static LatLng locschool6 = new LatLng(SchoolMapsLocations.latitude6, SchoolMapsLocations.longitude6);
    public static String title6 = "Araullo High School";

    public static double latitude7 = 14.598569;
    public static double longitude7 = 120.9892533;
    public static LatLng locschool7 = new LatLng(SchoolMapsLocations.latitude7, SchoolMapsLocations.longitude7);
    public static String title7 = "Centro Escolar University - Manila";

    public static double latitude8 = 14.5932609;
    public static double longitude8 = 120.9743883;
    public static LatLng locschool8 = new LatLng(SchoolMapsLocations.latitude8, SchoolMapsLocations.longitude8);
    public static String title8 = "Colegio de San Juan de Letran";

    public static double latitude9 = 14.597662;
    public static double longitude9 = 120.9923453;
    public static LatLng locschool9 = new LatLng(SchoolMapsLocations.latitude9, SchoolMapsLocations.longitude9);
    public static String title9 = "College of Holy Spirit";

    public static double latitude10 = 14.6038621;
    public static double longitude10 = 120.984246;
    public static LatLng locschool10 = new LatLng(SchoolMapsLocations.latitude10, SchoolMapsLocations.longitude10);
    public static String title10 = "Far Eastern University - Manila";

    public static double latitude11 = 14.5974506;
    public static double longitude11 = 120.9799315;
    public static LatLng locschool11 = new LatLng(SchoolMapsLocations.latitude11, SchoolMapsLocations.longitude11);
    public static String title11 = "Feati University";

    public static double latitude12 = 14.6000036;
    public static double longitude12 = 120.9828521;
    public static LatLng locschool12 = new LatLng(SchoolMapsLocations.latitude12, SchoolMapsLocations.longitude12);
    public static String title12 = "Guzman College of Science and Technology";

    public static double latitude13 = 14.5971175;
    public static double longitude13 = 120.9707379;
    public static LatLng locschool13 = new LatLng(SchoolMapsLocations.latitude13, SchoolMapsLocations.longitude13);
    public static String title13 = "Jose Abad Santos High School";

    public static double latitude14 = 14.597278;
    public static double longitude14 = 120.9908623;
    public static LatLng locschool14 = new LatLng(SchoolMapsLocations.latitude14, SchoolMapsLocations.longitude14);
    public static String title14 = "La Consolacion College - Manila";

    public static double latitude15 = 14.5915772;
    public static double longitude15 = 120.9842066;
    public static LatLng locschool15 = new LatLng(SchoolMapsLocations.latitude15, SchoolMapsLocations.longitude15);
    public static String title15 = "Lyceum of the Philippines - Manila";

    public static double latitude16 = 14.580584;
    public static double longitude16 = 120.9841263;
    public static LatLng locschool16 = new LatLng(SchoolMapsLocations.latitude16, SchoolMapsLocations.longitude16);
    public static String title16 = "Manila High School";

    public static double latitude17 = 14.5990372;
    public static double longitude17 = 120.9843616;
    public static LatLng locschool17 = new LatLng(SchoolMapsLocations.latitude17, SchoolMapsLocations.longitude17);
    public static String title17 = "Manuel L. Quezon University";

    public static double latitude18 = 14.590373;
    public static double longitude18 = 120.9759193;
    public static LatLng locschool18 = new LatLng(SchoolMapsLocations.latitude18, SchoolMapsLocations.longitude18);
    public static String title18 = "Mapua University - Manila";

    public static double latitude19 = 14.5980775;
    public static double longitude19 = 120.9869339;
    public static LatLng locschool19 = new LatLng(SchoolMapsLocations.latitude19, SchoolMapsLocations.longitude19);
    public static String title19 = "National Teachers College";

    public static double latitude20 = 14.5756449;
    public static double longitude20 = 120.9869273;
    public static LatLng locschool20 = new LatLng(SchoolMapsLocations.latitude20, SchoolMapsLocations.longitude20);
    public static String title20 = "Philippine Christian University";

    public static double latitude21 = 14.6015935;
    public static double longitude21 = 120.9806925;
    public static LatLng locschool21 = new LatLng(SchoolMapsLocations.latitude21, SchoolMapsLocations.longitude21);
    public static String title21 = "Philippine College of Criminology";

    public static double latitude22 = 14.597644;
    public static double longitude22 = 120.9707683;
    public static LatLng locschool22 = new LatLng(SchoolMapsLocations.latitude22, SchoolMapsLocations.longitude22);
    public static String title22 = "Raja Soliman Science and Technology High School";

    public static double latitude23 = 14.5936082;
    public static double longitude23 = 120.9871477;
    public static LatLng locschool23 = new LatLng(SchoolMapsLocations.latitude23, SchoolMapsLocations.longitude23);
    public static String title23 = "Ramon Avanencia High School";

    public static double latitude24 = 14.5961835;
    public static double longitude24 = 120.9940096;
    public static LatLng locschool24 = new LatLng(SchoolMapsLocations.latitude24, SchoolMapsLocations.longitude24);
    public static String title24 = "Saint Jude Catholic School";

    public static double latitude25 = 14.5741867;
    public static double longitude25 = 2520.9960225;
    public static LatLng locschool25 = new LatLng(SchoolMapsLocations.latitude25, SchoolMapsLocations.longitude25);
    public static String title25 = "Saint Paul University";

    public static double latitude26 = 14.60267894;
    public static double longitude26 = 120.9839173;
    public static LatLng locschool26 = new LatLng(SchoolMapsLocations.latitude26, SchoolMapsLocations.longitude26);
    public static String title26 = "San Beda University – Manila";

    public static double latitude27 = 14.6004827;
    public static double longitude27 = 120.9880347;
    public static LatLng locschool27 = new LatLng(SchoolMapsLocations.latitude27, SchoolMapsLocations.longitude27);
    public static String title27 = "San Sebastian College – Recolletos (Manila)";

    public static double latitude28 = 14.585494;
    public static double longitude28 = 120.9812523;
    public static LatLng locschool28 = new LatLng(SchoolMapsLocations.latitude28, SchoolMapsLocations.longitude28);
    public static String title28 = "Santa Isabel College";

    public static double latitude29 = 14.6021315;
    public static double longitude29 = 120.9844128;
    public static LatLng locschool29 = new LatLng(SchoolMapsLocations.latitude29, SchoolMapsLocations.longitude29);
    public static String title29 = "STI - Recto";

    public static double latitude30 = 14.595315;
    public static double longitude30 = 120.9857883 ;
    public static LatLng locschool30 = new LatLng(SchoolMapsLocations.latitude30, SchoolMapsLocations.longitude30);
    public static String title30 = "Technological Institute of the Philippines - Manila";

    public static double latitude31 = 14.5918982;
    public static double longitude31 = 120.979291;
    public static LatLng locschool31 = new LatLng(SchoolMapsLocations.latitude31, SchoolMapsLocations.longitude31);
    public static String title31 = "Unibersidad De Manila";

    public static double latitude32 = 14.6016564;
    public static double longitude32 = 120.9866926;
    public static LatLng locschool32 = new LatLng(SchoolMapsLocations.latitude32, SchoolMapsLocations.longitude32);
    public static String title32 = "University of the East - Manila";

    public static double latitude33 = 14.5979211;
    public static double longitude33 = 120.9899693;
    public static LatLng locschool33 = new LatLng(SchoolMapsLocations.latitude33, SchoolMapsLocations.longitude33);
    public static String title33 = "Victorino Mapa High School";




}
