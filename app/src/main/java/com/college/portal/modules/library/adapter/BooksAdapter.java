package com.college.portal.modules.library.adapter;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.modules.library.BookListActivity;
import com.college.portal.modules.library.model.Books;

import java.io.File;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private final Context mContext;
    private final Activity mActivity;
    private final List<Books> mList;

    public BooksAdapter(Activity mActivity, Context mContext, List<Books> mList) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_e_book, parent, false);
        return new BooksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {

        // Card Animation
        holder.viewHolder.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        Books book = mList.get(position);

        // Values
        if (!book.getPublishDate().equals("null")) {
            holder.publishDate.setText(String.format(mContext.getString(R.string.publish_on_placeholder), book.getPublishDate()));
        } else {
            holder.publishDate.setVisibility(View.GONE);
        }
        holder.eBookTitle.setText(book.getBookTitle());
        holder.eBookAuthor.setText(book.getBookAuthor());
        holder.bookPublisher.setText(book.getPublisher());
        if (book.getBookPath().equals("null")) {
            holder.downloadBtn.setVisibility(View.GONE);
            holder.openBtn.setVisibility(View.GONE);
        } else {
            downloadOpenFile(book.getBookPath(), holder.downloadBtn, holder.openBtn);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void downloadOpenFile(final String assi_path, Button downloadBtn, Button openBtn) {

        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + assi_path);

        if (myFile.exists()) {
            downloadBtn.setVisibility(View.GONE);
            openBtn.setVisibility(View.VISIBLE);

            openBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (BookListActivity.hasStoragePermission(mContext)) {
                        Uri uri = FileProvider.getUriForFile(mContext, "com.college.portal" + ".provider", myFile);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mContext.startActivity(intent);
                    } else {
                        BookListActivity.requestStoragePermission(AppApi.STORAGE_PERMISSION, mActivity);
                    }
                }
            });
        } else {
            openBtn.setVisibility(View.GONE);
            downloadBtn.setVisibility(View.VISIBLE);
            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BookListActivity.hasStoragePermission(mContext)) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(RetroApi.EBOOKS_FILE_PATH + assi_path + ""));
                        //Log.i("URI", "onClick: " + Uri.parse(RetroApi.ASSIGNMENT_FILE_PATH + assi_path + ""));
                        request.setTitle(assi_path);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE);
                        request.setMimeType("application/pdf");
                        request.allowScanningByMediaScanner();
                        request.setAllowedOverMetered(true);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, assi_path);

                        DownloadManager dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);
                    } else {
                        BookListActivity.requestStoragePermission(AppApi.STORAGE_PERMISSION, mActivity);
                    }
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView publishDate, eBookTitle, eBookAuthor, bookPublisher;
        Button downloadBtn, openBtn;
        CardView viewHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHolder = itemView.findViewById(R.id.view_holder);
            publishDate = itemView.findViewById(R.id.publish_date);
            bookPublisher = itemView.findViewById(R.id.book_publisher);
            eBookTitle = itemView.findViewById(R.id.ebook_title);
            eBookAuthor = itemView.findViewById(R.id.ebook_author);
            downloadBtn = itemView.findViewById(R.id.download_btn);
            openBtn = itemView.findViewById(R.id.open_btn);

        }
    }

}
