package org.tensorflow.lite.examples.detection.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.storage.Disease;

import java.util.ArrayList;

public class DiseaseAdapter extends ArrayAdapter<Disease> implements View.OnClickListener{

    private ArrayList<Disease> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView cant;
        ImageView img;
    }

    @Override
    public void onClick(View view) {

    }

    public DiseaseAdapter(ArrayList<Disease> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Disease dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.cant = convertView.findViewById(R.id.cant);
            viewHolder.img = convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(dataModel.getTitle());
        viewHolder.cant.setText(String.valueOf(dataModel.getCount()));
        viewHolder.img.setColorFilter(dataModel.getColor());
        // Return the completed view to render on screen
        return convertView;
    }
}
