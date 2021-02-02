package com.master.design.eighty.Utils;

//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

public class FirebaseStorageHelper {

//    private static final String TAG = FirebaseStorageHelper.class.getCanonicalName();
//
//    private FirebaseStorage firebaseStorage;
//
//    private StorageReference rootRef;
//
//    private Context context;
//
//    public FirebaseStorageHelper(Context context){
//        this.context = context;
//        init();
//    }
//
//    private void init(){
//        this.firebaseStorage = FirebaseStorage.getInstance();
//        rootRef = firebaseStorage.getReferenceFromUrl("add your Firebase database path");
//    }
//
//    public void saveProfileImageToCloud(String userId, Uri selectedImageUri, final ImageView imageView) {
//
//        StorageReference photoParentRef = rootRef.child(userId);
//        StorageReference photoRef = photoParentRef.child(selectedImageUri.getLastPathSegment());
//        UploadTask uploadTask = photoRef.putFile(selectedImageUri);
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "OnFailure " + e.getMessage());
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
//                Glide.with(context).load(downloadUrl.getPath()).into(imageView);
//            }
//        });
//
//    }
}