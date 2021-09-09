package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.domain.Watchlist;
import eu.opensource.portfolioclient.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/watchlist")
@Controller
public class WatchListController {

    private final WatchListService watchListService;

    @GetMapping("/{watchlistId}")
    public String getWatchlist(@PathVariable Long watchlistId, @PageableDefault Pageable pageable, Model uiModel) {

        Watchlist watchlist = watchListService.getWatchlist(watchlistId, pageable);

        uiModel.addAttribute("page", pageable.getPageNumber());
        uiModel.addAttribute("size", pageable.getPageSize());
        uiModel.addAttribute("watchlistName", watchlist.getName());
        uiModel.addAttribute("watchlist", watchlist);

        return "watchlist/watchlist";
    }
}
