package com.example.myadvancedapp

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myadvancedapp.data.User

class UserAdapter(
    private var users: List<User>,
    private val onItemClick: (User) -> Unit,
    private val onLongClick: (User, View) -> Boolean
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val ivMore: ImageView = itemView.findViewById(R.id.ivMore)
        val tvInitials: TextView = itemView.findViewById(R.id.tvInitials)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        
        // Set initials
        if (user.name.isNotEmpty()) {
            holder.tvInitials.text = user.name.first().toString().uppercase()
        }
        
        holder.itemView.setOnClickListener { onItemClick(user) }
        
        holder.itemView.setOnLongClickListener { 
             onLongClick(user, it)
        }

        // 6.3 Implement Popup Menu for quick actions inside list items
        holder.ivMore.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.popup_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_popup_info -> {
                        // Show info in a proper dialog
                        val builder = AlertDialog.Builder(view.context)
                        builder.setTitle("User Info")
                        builder.setMessage("Name: ${user.name}\nUsername: ${user.username}\nEmail: ${user.email}\nWebsite: ${user.website}")
                        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        builder.show()
                        true
                    }
                    R.id.action_popup_share -> {
                        // Implement actual share functionality
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this user: ${user.name} (${user.email}) - ${user.website}")
                        view.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount() = users.size
    
    fun updateData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}