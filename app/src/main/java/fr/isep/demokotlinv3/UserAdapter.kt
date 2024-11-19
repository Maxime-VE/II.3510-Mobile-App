package fr.isep.demokotlinv3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import fr.isep.demokotlinv3.models.UserModel

// Note: Adapter allows a connection between the data and UI elements.
// Here we use it to fill a RecyclerView a component holding user information item (item_user.xml)
class UserAdapter(private val userList: RealmResults<UserModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Hold the elements of RecyclerView as a ViewHolder
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_view_name)
        val ageTextView: TextView = itemView.findViewById(R.id.text_view_age)
    }

    // Create a new item_view element to be filled with user data and to place into RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Fill the item view with the user information
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        if (user != null) {
            holder.nameTextView.text = user.name
            holder.ageTextView.text = "Age: ${user.age}"
        }
    }

    // Mandatory Abstract class
    override fun getItemCount(): Int {
        return userList.size
    }

}
