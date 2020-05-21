 storageReference.child("Uploads").child(fileName).putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      //Uri url=taskSnapshot.getDownloadUrl();
                     //  String url= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        String url= urlTask.getResult().toString();

                        DatabaseReference reference=database.getReference();
                        reference.child(fileNamel).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "Successfull Upload", Toast.LENGTH_SHORT).show();
                                    mtext.setText("SuccessFull");
                                    progressDialog.cancel();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "No Successful Upload", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "No Successful Upload", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.incrementProgressBy((int) progress);

                    }
                });
            }
        });
