package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.R;

import java.util.List;

/**
 * Created by snielson on 6/10/16.
 */
public class SupportContactsArrayAdapter extends ArrayAdapter<SupportContact> {

  public SupportContactsArrayAdapter(Context context, List<SupportContact> users) {
    super(context, 0, users);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    // Get the data item for this position
    SupportContact user = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_support_contact, parent, false);
    }
    // Lookup view for data population
    TextView tvName = (TextView) convertView.findViewById(R.id.contactListItemName);
    TextView tvPhone = (TextView) convertView.findViewById(R.id.contactListItemPhone);
    // Populate the data into the template view using the data object
    tvName.setText(user.getName());
    tvPhone.setText(user.getPhoneNumber());
    // Return the completed view to render on screen
    return convertView;
  }
}
