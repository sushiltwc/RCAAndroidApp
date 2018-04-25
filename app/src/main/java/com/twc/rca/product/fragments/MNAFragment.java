package com.twc.rca.product.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.twc.rca.R;

/**
 * Created by TWC on 21-02-2018.
 */

public class MNAFragment extends Fragment {

    TextView tv_content;
    WebView webView;

    String content = "<h1>About Dubai Visa</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " \n" +
            " 96 Hours Dubai Tourist Visa<br>\n" +
            " Short Term Single Entry<br>\n" +
            " 96 hours Transit Visa allows you to enter UAE and stay for a maximum of 96 hours (4 days). This visa is applicable only to passengers who have a stopover at Dubai International Airport.<br><br>\n" +
            " \n" +
            " 14 Days Dubai Tourist Visa<br>\n" +
            " Short Term Single Entry<br>\n" +
            " 14 Days Visa allows passengers to stay in UAE for upto 14 days. This visa is ideal for tourists and business travelers.<br><br>\n" +
            " \n" +
            " 30 Days Dubai Tourist Visa<br>\n" +
            " Short Term Single Entry<br>\n" +
            " 30 Days Visa allows passengers to stay in UAE for upto 30 days. This visa is ideal for tourists, business travelers or people visiting family &amp; relatives.<br><br>\n" +
            " \n" +
            " 90 Days Dubai Tourist Visa<br>\n" +
            " Long Term Single Entry<br>\n" +
            " 90 Days Visa allows passengers to stay in UAE for upto 90 days. This visa is ideal for passengers who wish to stay for a longer period either to visit their family or relatives.<br><br>\n" +
            " \n" +
            " </div>\n" +
            " </div>\n" +
            " <h1>Dubai Visa Process</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " \n" +
            " Usually, it will take 4 – 5 working days from the date of submission. Since UAE is closed on Friday &amp; Saturday and India is closed on Sunday, processing your Visa over the weekend may take longer. Visa Processing time during peak seasons like Dubai Shopping Festivals may also take longer.\n" +
            " \n" +
            " </div>\n" +
            " </div>\n" +
            " <h1>Documents Required</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " <ul class=\"numb-list\">\n" +
            "  \t<li>Scanned copy of the first and last page of your passport</li>\n" +
            "  \t<li>Scanned copy of your passport size photograph</li>\n" +
            "  \t<li>Confirmed return air ticket (not mandatory for application)</li>\n" +
            " </ul>\n" +
            " </div>\n" +
            " </div>\n" +
            " <h1>Why RedCarpet Assist</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " \n" +
            " Applying for your Dubai visa gets quicker and easier with RedCarpet Assist. Simply select your desired visa type, provide the required details and get your Dubai visa online in just 48 working hours. Get started with your visa\n" +
            " \n" +
            " </div>\n" +
            " </div>\n" +
            " <h1>What our customer says</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " \n" +
            " RedCarpet Assist served more than <b>20,000 happy customers </b>in last 1 year, and we are enjoying to do so.\n" +
            " Here are few amazing customers saying few lines about us\n" +
            " \n" +
            " </div>\n" +
            " </div>\n" +
            " [testimonial_rotator id=\"1771\"]\n" +
            " <h1>Notes</h1>\n" +
            " <div class=\"row\">\n" +
            " <div class=\"col-md-12 col-lg-8 col-xl-8\">\n" +
            " <ul class=\"numb-list\">\n" +
            "  \t<li>We do not provide work/job visas</li>\n" +
            "  \t<li>In case of the 96-hour transit UAE Visa it is mandatory that the Entry and Exit airport should be Dubai International Airport only</li>\n" +
            "  \t<li>96 hours UAE visas are specifically applicable to applicants transiting through Dubai International Airport.</li>\n" +
            "  \t<li>Applicant should hold a confirmed ticket to an onward destination and not returning to point of origin.</li>\n" +
            "  \t<li>Female passengers irrespective of any age should either be accompanied by her husband, adult brother, father, adult son or should be visiting an immediate relative. In either case, name of the accompanying passenger / relative in UAE is required to be provided in the application indicating the relationship</li>\n" +
            " </ul>\n" +
            " </div>\n" +
            " </div>\n" +
            " <div class=\"row accord-default\">\n" +
            " <div class=\"col-md-12 col-lg-10 col-xl-10\">\n" +
            " <h1>FAQs</h1>\n" +
            " [WPSM_FAQ id=18197]\n" +
            " \n" +
            " </div>\n" +
            " </div>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mna, container, false);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        webView = (WebView) view.findViewById(R.id.webView);
        //tv_content.setText(Html.fromHtml(content));
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

       /* Document htmlDocument = Jsoup.parse(content);
        tv_content.setText(htmlDocument.body().text());
*/
        return view;
    }

    public static MNAFragment getInstance() {
        MNAFragment mnaFragment = new MNAFragment();
        return mnaFragment;
    }
}
