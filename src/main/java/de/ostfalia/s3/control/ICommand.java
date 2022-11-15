package de.ostfalia.s3.control;

public interface ICommand {

    void execute();
    ICommand undo();

}
