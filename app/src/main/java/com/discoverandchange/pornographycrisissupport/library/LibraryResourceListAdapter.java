package com.discoverandchange.pornographycrisissupport.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.R;

import java.util.List;

/**
 *
 */
public class LibraryResourceListAdapter extends ArrayAdapter<LibraryResource> {
  private List<LibraryResource> resources;

  public LibraryResourceListAdapter(Context context, List<LibraryResource> resources) {
    super(context, 0, resources);
    this.resources = resources;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LibraryResource item = getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_library_resource, parent, false);
    }

    TextView title = (TextView)convertView.findViewById(R.id.resourceListItemName);
    if (item.getTitle() != null) {
      title.setText(item.getTitle());
    }
    else {
      title.setText("<Title Missing>");
    }

    TextView description = (TextView)convertView.findViewById(R.id.resourceListItemDescription);
    if (item.getDescription() != null) {
      description.setText(item.getDescription());
    }
    else {
      description.setText("");
    }

    return convertView;
  }
}
