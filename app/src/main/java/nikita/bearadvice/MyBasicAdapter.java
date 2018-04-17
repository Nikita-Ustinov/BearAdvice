package nikita.bearadvice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nikita.bearadvice.Item;
import nikita.bearadvice.R;

public class MyBasicAdapter extends BaseAdapter{

    Context context;
    List<Item> rowItems;

    public MyBasicAdapter (Context context, List<Item> items) {
        this.context = context;
        this.rowItems = items;
    }

    //класс для показа одной строчки
    private class ViewHolder {
        TextView textView;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hld = null;

        LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = lInflater.inflate(R.layout.item_row, null);
            hld.textView = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(hld);
        }
        else {
            hld = (ViewHolder) convertView.getTag();
        }

        Item item = (Item) getItem(position);
        hld.textView.setText(item.getName());

        return convertView;
    }


    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

}
