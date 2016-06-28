package com.discoverandchange.pornographycrisissupport.library;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.discoverandchange.pornographycrisissupport.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 */
public class LibraryResourceListAdapter extends ArrayAdapter<LibraryResource> {
  private List<LibraryResource> resources;
  private Context context;

  public LibraryResourceListAdapter(Context context, List<LibraryResource> resources) {
    super(context, 0, resources);
    this.context = context;
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

    // set our thumbnail if we have one
    ImageView imageView = (ImageView)convertView.findViewById(R.id.resourceListItemImage);
    String thumbnail = item.getThumbnail();

    // cancel any request that may be going on right now.
    Picasso.with(context).cancelRequest(imageView);
    // if we have an external resource let's load that.
    if (thumbnail != null && thumbnail.startsWith("http")) {
      // load the resource as an http
      Picasso.with(context).load(thumbnail).fit().into(imageView);
    }
    else {
      // load our android resource thumbnail into the image view.
      imageView.setImageResource(item.getDefaultThumbnailResource());
      //Picasso.with(context).load(R.drawable.ic_assessment_black_48dpi).into(imageView);
      //Picasso.with(context).load(item.getDefaultThumbnailResource()).into(imageView);
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
