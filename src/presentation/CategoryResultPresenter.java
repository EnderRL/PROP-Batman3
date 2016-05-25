package presentation;

import view.CategoryResultView;
import view.Config;
import view.MyApp;
import view.RelevanceTypeSelectorView;

import java.util.ArrayList;

public class CategoryResultPresenter extends BasePresenter  {

    private ArrayList<String> result;
    private int index;
    private int lastSelected;

    public CategoryResultPresenter(int type) {
        result = domainController.secondSearch(type);
        index = 0;
        actualView = new CategoryResultView(this);
        show();
        MyApp.startScene(actualView.getContent());
    }

    public void onClick(int index) {
        lastSelected = index;
        ((CategoryResultView)actualView).askSimilarOp();
    }

    //op < 0 -> menos relevante
    //op == 0 -> igual de relevante
    //op > 0 -> más relevante
    public void onAcceptClick(int op) {
        ArrayList<String> nextResult = domainController.searchSimilarRelevance(result.get(lastSelected), op);
        actualView.destroy();
        actualView = null;
        MyApp.changePresenter(new SimilarRelevancePresenter(result));
    }

    public void showMore() {
        if (index + Config.LISTS_SIZE <= result.size()) {
            index += Config.LISTS_SIZE;
            show();
        }
    }

    public void showLess() {
        if (index - Config.LISTS_SIZE >= 0) {
            index = index - Config.LISTS_SIZE;
            show();
        }

    }

    private void show() {
        int max = index+Config.LISTS_SIZE;
        if (max > result.size()) max = result.size();
        max -= index;
        for (int i = 0; i < max; ++i) {
            ((CategoryResultView) actualView).setContent(index+i, result.get(index+i));
        }
        max += index;
        for (;max < index+Config.LISTS_SIZE; ++max) {
            ((CategoryResultView) actualView).setContent(max, "\t \t \t \t");
        }
    }

}
