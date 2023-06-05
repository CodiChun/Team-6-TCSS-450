package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentContactCardBinding;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private final HashMap<Integer,Contact> mContacts;
    private ManagerFriendViewModel mManage;

    /**
     * Constructor
     // * @param context current context
     * @param contacts current contacts
     */
    public ContactRecyclerViewAdapter(HashMap<Integer,Contact> contacts){
        this.mContacts = contacts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_contact_card,parent,false);
        mManage = new ViewModelProvider(
                (ViewModelStoreOwner) MainActivity.getActivity()).get(ManagerFriendViewModel.class);


        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(contact.getUserName());
        //holder.fullName.setText(contact.getFirstName() +" "+contact.getLastName());

        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_addFriendsFragment_to_chatRoomFragment));

        // contact card remove button action
        holder.removeButton.setOnClickListener(button ->
                showRemoveDialog(contact, holder.view, position));
    }

    /**
     * Show Remove Dialog for removing friends
     * @param contact the contact
     * @param view the view for it to appear
     * @param position position of the contact
     */
    void showRemoveDialog(Contact contact, View view, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialog_contact_card_remove_friend);
        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectRemoveFriend(contact.getUserID());
            removeFromView(position);
            Toast.makeText(MainActivity.getActivity(),"Removed friend successful!", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
        dialog.show();
    }

    /**
     * Remove contact from list
     * @param position position of the contact
     */
    private void removeFromView(int position){
        mContacts.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, mContacts.size());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Contact Card Recycler View.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nickname;
        //private final TextView fullName;
        private final ImageView messageButton;
        private final ImageView removeButton;
        //private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_name);
            //fullName = itemView.findViewById(R.id.contact_card_fullname);
            messageButton = itemView.findViewById(R.id.button_chat);
            removeButton = itemView.findViewById(R.id.button_delete);
            //cardLayout = itemView.findViewById(R.id.contact_card_root);
            view = itemView.getRootView();
        }
    }

}
