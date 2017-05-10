package id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate;

import java.io.Serializable;

/**
 * Created by paymin on 10/05/2017.
 */

public class BannerItem implements Serializable {

    private String imageUrl;

    public BannerItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
