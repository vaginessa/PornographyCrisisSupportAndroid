package com.discoverandchange.pornographycrisissupport.supportnetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.R;

import java.util.List;


/**
 * Creates an ArrayAdapter using information from our support network contacts list to allow the
 * user to interact with the list.
 *
 * @author Stephen Nielson
 */
public class SupportContactsArrayAdapter extends ArrayAdapter<SupportContact> {

  private List<SupportContact> userList;

  /**
   * An ArrayAdapter to allow user interaction with the support contacts list.
   *
   * @param context Information about where the list is created
   * @param users   Information about the users in the array
   */
  public SupportContactsArrayAdapter(Context context, List<SupportContact> users) {
    super(context, 0, users);
    this.userList = users;
  }

  /**
   * Generates the view itself using information about our support network contacts.
   *
   * @param position    Which contact in the list was interacted with
   * @param convertView Allows information about a support contact to be made visual
   * @param parent      Information about where the list of support contacts will be displayed
   * @return The completed view to be displayed on the user's screen
   */
  public View getView(final int position, View convertView, ViewGroup parent) {

    // Get the data item for this position
    final SupportContact user = getItem(position);

    // Check if an existing view is being reused, otherwise inflate the view

    if (convertView == null) {
      if (user.isCrisisContact()) {
        convertView = LayoutInflater.from(getContext()).inflate(
            R.layout.item_support_contact_crisis, parent, false);
      } else {
        convertView = LayoutInflater.from(getContext()).inflate(
            R.layout.item_support_contact, parent, false);
      }
    }

    if (user.isCrisisContact() && convertView.getId() != R.id.supportContactCrisisItemRow) {
      convertView = LayoutInflater.from(getContext()).inflate(
          R.layout.item_support_contact_crisis, parent, false);
    } else if (!user.isCrisisContact() && convertView.getId() != R.id.supportContactItemRow) {
      convertView = LayoutInflater.from(getContext()).inflate(
          R.layout.item_support_contact, parent, false);
    }

    final TextView tvName = (TextView) convertView.findViewById(R.id.contactListItemName);

    // Lookup view for data population
    // Populate the data into the template view using the data object

    if (user.getName() != null) {

      tvName.setText(user.getName());
    }

    // Listen for the delete button event
    Button delete = (Button) convertView.findViewById(R.id.contactListItemDelete);


    // A variable to manipulate / remove from array adapter
    final SupportContactsArrayAdapter adapter = this;

    delete.setOnClickListener(new View.OnClickListener() {

      /**
       * Allows the user to delete a contact from the support network.
       * @param view Allows the view which holds a support network contact to be interacted with
       */
      public void onClick(View view) {
        // Remove the user from the support network service
        SupportNetworkService removeService = SupportNetworkService.getInstance(getContext());
        removeService.removeSupportContact(user.getContactId());
        userList.remove(position);
        // Remove the user from the adapter
        adapter.notifyDataSetChanged();
      }
    });

    // Return the completed view to render on screen
    return convertView;


  }


}
