package jpabook.jpashop.web.item;

import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items.stream().map(ItemResponse::new).collect(Collectors.toList()));
        return "items/itemsList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String create(@Validated BookForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        Item item = Book.builder()
                .name(form.getName())
                .price(Money.of(Long.valueOf(form.getPrice())))
                .stockQuantity(Quantity.of(form.getStockQuantity()))
                .author(form.getAuthor())
                .isbn(form.getIsbn())
                .build();
        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping("/book/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book book = itemService.findBookById(itemId);
        model.addAttribute("form", new BookResponse(book));
        return "items/updateItemForm";
    }

    @PostMapping("/book/{itemId}/edit")
    public String updateBook(@PathVariable("itemId") Long itemId, @Validated BookForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/updateItemForm";
        }

        itemService.updateBook(itemId, form.createBook());
        return "redirect:/items";
    }

}
