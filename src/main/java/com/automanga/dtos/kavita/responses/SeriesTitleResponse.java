package com.automanga.dtos.kavita.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class SeriesTitleResponse {
    private int id;
    private String name;
    private String localizedName;

//    private String originalName;
//    private String sortName;
//    private int pages;
//    private boolean coverImageLocked;
//    private int pagesRead;
//    private Date latestReadDate;
//    private String lastChapterAdded;
//    private int userRating;
//    private boolean hasUserRated;
//    private int format;
//    private Date created;
//    private boolean nameLocked;
//    private boolean sortNameLocked;
//    private boolean localizedNameLocked;
//    private int wordCount;
//    private int libraryId;
//    private String libraryName;
//    private int minHoursToRead;
//    private int maxHoursToRead;
//    private int avgHoursToRead;
//    private String folderPath;
//    private Date lastFolderScanned;
}
