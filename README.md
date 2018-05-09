# BU Library Course Reserves App

This Android app is designed for students at Boston University who use course materials on reserve in the library system.  The app connects to the [Alma library management system](http://www.exlibrisgroup.com/products/alma-library-services-platform/), and allows a student to search for current courses, save books and materials attached to those courses, and display the current availability of those items at any time.  Its primary feature is an alart system that periodically checks for changes in availability, allowing users to focus on their studies instead of waiting for an item to be returned.

## Getting Started

Making requests into any Alma database requires an [API key](https://developers.exlibrisgroup.com/alma/apis), which can generally only be obtained if you belong to an institution that is an Ex Libris customer.  Ensure your application is set up with access to both the "Bibs" and "Courses" APIs.

![](fill_in_src_url)

Unlike in the image, make sure your current plan for the APIS is for the live environment, not a sandbox. You should receive an API key like `l7xx3fc758eddb30fcda874ae3251490e30f` (not a real key).  Create a new `secrets.xml` file in your local repo for this project within the  `res/values` folder:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="apikey">l7xx3fc758eddb30fcda874ae3251490e30f</string>
</resources>
```

Your project should now be able to communicate with the Alma database.
