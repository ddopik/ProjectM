package com.spade.mek.ui.more.contact_us.view;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.contact_us.model.Area;
import com.spade.mek.ui.more.contact_us.model.ContactUsInfo;
import com.spade.mek.ui.more.contact_us.presenter.ContactUsPresenter;
import com.spade.mek.ui.more.contact_us.presenter.ContactUsPresenterImpl;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class ContactUsFragment extends BaseFragment implements ContactUsView, OnMapReadyCallback {

    private View contactUsView;
    private ContactUsPresenter contactUsPresenter;
    private TextView phoneNumber, website, emailAddress, call;
    private ProgressBar progressBar;
    private AreasSpinnerAdapter areasSpinnerAdapter;
    private AppCompatSpinner areaListSpinner;
    private List<Area> areaList;
    private GoogleMap mMap;
    private ImageView facebook, twitter, instagram, transparentImage;
    private String facebookUrl, instagramUrl, twitterUrl;
    private ScrollView mainScroll;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactUsView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initViews();
        overrideFonts(getContext(), contactUsView);
        return contactUsView;
    }

    @Override
    protected void initPresenter() {
        contactUsPresenter = new ContactUsPresenterImpl(getContext());
        contactUsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        areaList = new ArrayList<>();

        Button sendMessage = (Button) contactUsView.findViewById(R.id.message_us_btn);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        call = (TextView) contactUsView.findViewById(R.id.call_number);
        emailAddress = (TextView) contactUsView.findViewById(R.id.email_text);
        phoneNumber = (TextView) contactUsView.findViewById(R.id.phone_text);
        website = (TextView) contactUsView.findViewById(R.id.website_text);

        facebook = (ImageView) contactUsView.findViewById(R.id.facebook_image);
        instagram = (ImageView) contactUsView.findViewById(R.id.instagram_image);
        twitter = (ImageView) contactUsView.findViewById(R.id.twitter_image);
        transparentImage = (ImageView) contactUsView.findViewById(R.id.transparent_image);

        mainScroll = (ScrollView) contactUsView.findViewById(R.id.main_scroll);

        progressBar = (ProgressBar) contactUsView.findViewById(R.id.progress_bar);

        areaListSpinner = (AppCompatSpinner) contactUsView.findViewById(R.id.areas_spinner);

        contactUsPresenter.getContactData(appLang);

        areasSpinnerAdapter = new AreasSpinnerAdapter(areaList, getContext());
        areaListSpinner.setAdapter(areasSpinnerAdapter);
        areaListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area area = (Area) parent.getItemAtPosition(position);
                if (position != 0) {
                    showStoreLocation(area);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sendMessage.setOnClickListener(v -> startActivity(SendMessageActivity.getLaunchIntent(getContext())));
        call.setOnClickListener(v -> contactUsPresenter.callNumber(getActivity(), phoneNumber.getText().toString()));
        emailAddress.setOnClickListener(v -> contactUsPresenter.sendMail(emailAddress.getText().toString()));
        website.setOnClickListener(v -> contactUsPresenter.openUrl(website.getText().toString()));
        facebook.setOnClickListener(v -> contactUsPresenter.openUrl(facebookUrl));
        instagram.setOnClickListener(v -> contactUsPresenter.openUrl(instagramUrl));
        twitter.setOnClickListener(v -> contactUsPresenter.openUrl(twitterUrl));

        transparentImage.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    return false;

                case MotionEvent.ACTION_UP:
                    mainScroll.requestDisallowInterceptTouchEvent(false);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    return false;
                default:
                    return true;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void showStoreLocation(Area area) {
        Log.d("Map", area.getArea() + " .. " + area.getLat() + " .. " + area.getLng());
        double lat = Double.parseDouble(area.getLat());
        double lng = Double.parseDouble(area.getLng());
        LatLng storeLocation = new LatLng(lat, lng);
//        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(storeLocation).title(area.getArea()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(storeLocation)
                .zoom(13)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onError(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {

    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showData(ContactUsInfo contactUsInfo) {
        if (contactUsInfo.getPhone() != null && !contactUsInfo.getPhone().isEmpty()) {
            call.setVisibility(View.VISIBLE);
        }
        if (contactUsInfo.getFacebook() != null && !contactUsInfo.getFacebook().isEmpty()) {
            facebookUrl = contactUsInfo.getFacebook();
            facebook.setVisibility(View.VISIBLE);
        }
        if (contactUsInfo.getInstagram() != null && !contactUsInfo.getInstagram().isEmpty()) {
            instagramUrl = contactUsInfo.getInstagram();
            instagram.setVisibility(View.VISIBLE);
        }
        if (contactUsInfo.getTwitter() != null && !contactUsInfo.getTwitter().isEmpty()) {
            twitterUrl = contactUsInfo.getTwitter();
            twitter.setVisibility(View.VISIBLE);
        }
        emailAddress.setText(contactUsInfo.getMail());
        website.setText(contactUsInfo.getWebsite());
        phoneNumber.setText(contactUsInfo.getPhone());

        Area area = new Area();
        area.setArea(getString(R.string.area));
        areaList.add(area);
        areaList.addAll(contactUsInfo.getAreas());
        areasSpinnerAdapter.notifyDataSetChanged();

        contactUsPresenter.requestLocationPermission(getActivity());
    }

    @Override
    public void setUserLocation(Location userLocation) {
        Map<Float, Area> locationsDistance = new HashMap<>();
        for (int i = 1; i < areaList.size(); i++) {
            Area area = areaList.get(i);
            float distance = contactUsPresenter.calculateDistance(Double.parseDouble(area.getLat()), Double.parseDouble(area.getLng()), userLocation.getLatitude(), userLocation.getLongitude());
            locationsDistance.put(distance, area);
        }
        Area area = locationsDistance.get(Collections.min(locationsDistance.keySet()));
        showStoreLocation(area);
    }

}
