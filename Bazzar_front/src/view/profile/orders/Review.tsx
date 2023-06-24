import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Rating,
    TextField,
    Typography
} from "@mui/material";
import {useKeycloak} from "@react-keycloak/web";
import React, {useState} from "react";
import {primary} from "../../../Colors";
import {apiAddReview} from "../../../api/ReviewApi";
import {ReviewDto} from "../../../newInterfaces";

export function Review() {
    const [open, setOpen] = useState(false);
    const [value, setValue] = useState<number>(0);
    const [text, setText] = useState<string>("");
    const {keycloak, initialized} = useKeycloak();
    const [email, setEmail] = React.useState<string>(keycloak?.tokenParsed?.email ?? "");

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const payHandle = () => {
        if (value > 0) {
            if (text.length > 5 && email) {
                const data: ReviewDto = {
                    mark: value,
                    reviewText: text,
                    username: email,
                };
                apiAddReview(data).then(() => {
                    setOpen(false);
                }).catch(() => {
                    console.error("Ошибка при добавлении отзыва");
                })
            }
        }
    };

    return (
        <div>
            <button className="btn btn-sm text-white" style={{backgroundColor: primary}}
                    onClick={handleClickOpen}>Оставить отзыв
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Оставить отзыв</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Здесь вы можете оставить отзыв о товаре
                    </DialogContentText>
                    <br/>
                    <Typography component="legend">Поставьте оценку от 1 до 5</Typography>
                    <Rating
                        name="simple-controlled"
                        value={value}
                        onChange={(event, newValue) => {
                            setValue(newValue != null ? newValue : 0);
                        }}
                    />

                    <TextField
                        margin="dense"
                        id="name"
                        label="Отзыв"
                        type="text"
                        onChange={(event) => setText(event.currentTarget.value)}
                        fullWidth
                        multiline={true}
                        variant="standard"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={payHandle}>Отправить</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}