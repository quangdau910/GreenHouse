package com.quangdau.greenhouse.Spinner.spinnerLimitSetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quangdau.greenhouse.R;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<CategorySpinner> {
    private int itemPosition;
    public CategorySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<CategorySpinner> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_selected,parent,false);
        TextView tvSelecter = convertView.findViewById(R.id.textViewSelecterSpinner);
        CategorySpinner categorySpinner = this.getItem(position);
        tvSelecter.setText(categorySpinner.getText());
        itemPosition = position;
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_category,parent,false);
        TextView tvCategoryLanguage = convertView.findViewById(R.id.textViewCategorySpinner);
        CategorySpinner categorySpinner = this.getItem(position);
        tvCategoryLanguage.setText(categorySpinner.getText());
        return convertView;
    }

    public String getItemSelected(){
        CategorySpinner categorySpinner = this.getItem(itemPosition);
        return categorySpinner.getText();
    }
}
