package com.tohedul.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public UserAdapter( Context context, int resource) {
        super(context, resource);
    }


    public void add(User object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View row;
        row=convertView;
        UserHolder userHolder;
        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            userHolder=new UserHolder();
            userHolder.txtName=row.findViewById(R.id.NameView);
            userHolder.txtEmail=row.findViewById(R.id.EmailView);
            userHolder.txtAddress=row.findViewById(R.id.AddressView);
            userHolder.txtPhone=row.findViewById(R.id.PhoneView);
            row.setTag(userHolder);
        }else {
            userHolder=(UserHolder)row.getTag();
        }
        User user=(User) this.getItem(position);
        userHolder.txtName.setText(user.getName());
        userHolder.txtEmail.setText(user.getEmail());
        userHolder.txtAddress.setText(user.getAddress());
        userHolder.txtPhone.setText(user.getPhone());

        return row;
    }
    static class UserHolder{
        TextView txtName,txtEmail,txtAddress,txtPhone;
    }
}
